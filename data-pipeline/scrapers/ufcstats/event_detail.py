import time
from typing import Dict, Any, List

import requests
from bs4 import BeautifulSoup  # type: ignore
from tenacity import retry, stop_after_attempt, wait_exponential  # type: ignore

USER_AGENT = "FightIQBot/0.1 (+https://example.com)"


@retry(stop=stop_after_attempt(3), wait=wait_exponential(multiplier=1, min=1, max=8))
def fetch(url: str) -> str:
  headers = {"User-Agent": USER_AGENT}
  response = requests.get(url, headers=headers, timeout=20)
  response.raise_for_status()
  time.sleep(0.5)
  return response.text


def parse_event_detail(html: str) -> Dict[str, Any]:
  soup = BeautifulSoup(html, "lxml")
  title = soup.select_one("span.b-content__title-highlight")
  info = soup.select_one("div.b-list__info-box")
  name = title.text.strip() if title else None
  date = None
  location = None
  if info:
    for row in info.select("li"):
      label = (row.select_one("i") or {}).get("class", [])
      txt = row.text.strip()
      if "b-list__info-box-label" in " ".join(label):
        # not used
        pass
      if "Date:" in txt:
        date = txt.replace("Date:", "").strip()
      if "Location:" in txt:
        location = txt.replace("Location:", "").strip()
  bouts: List[Dict[str, Any]] = []
  for tr in soup.select("tr.b-fight-details__table-row"):
    fighters = tr.select("p.b-fight-details__table-text a.b-link")
    red = fighters[0].text.strip() if len(fighters) > 0 else None
    blue = fighters[1].text.strip() if len(fighters) > 1 else None
    method = tr.select_one("p.b-fight-details__table-text:has(span.b-fight-details__text-item_first)")
    method_text = method.text.strip() if method else None
    round_el = tr.select_one("p.b-fight-details__table-text:has(i.b-fight-details__label):contains('Round:')")
    round_val = None
    time_el = tr.select_one("p.b-fight-details__table-text:has(i.b-fight-details__label):contains('Time:')")
    time_val = None
    # fallback for round/time using columns
    cols = tr.select("td")
    if len(cols) >= 7:
      round_val = cols[6].text.strip()
    if len(cols) >= 8:
      time_val = cols[7].text.strip()
    bouts.append({
      "fighter_red": red,
      "fighter_blue": blue,
      "method": method_text,
      "round": round_val,
      "time": time_val
    })
  return {"name": name, "date": date, "location": location, "bouts": bouts}


