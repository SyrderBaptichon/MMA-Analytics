import time
from typing import Dict, Any

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


def parse_profile(html: str) -> Dict[str, Any]:
  soup = BeautifulSoup(html, "lxml")
  name_el = soup.select_one("span.b-content__title-highlight")
  name = name_el.text.strip() if name_el else None
  info = {}
  for li in soup.select("li.b-list__box-list-item"):
    txt = " ".join(li.text.split())
    if "Height:" in txt:
      info["height"] = txt.replace("Height:", "").strip()
    elif "Reach:" in txt:
      info["reach"] = txt.replace("Reach:", "").strip()
    elif "STANCE:" in txt or "Stance:" in txt:
      info["stance"] = txt.split(":")[-1].strip()
    elif "DOB:" in txt or "DOB" in txt:
      info["dob"] = txt.split(":")[-1].strip()
  return {"name": name, **info}


