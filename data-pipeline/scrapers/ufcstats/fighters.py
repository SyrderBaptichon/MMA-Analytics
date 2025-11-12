import time
from typing import Iterator, Dict, Any, List

import requests
from bs4 import BeautifulSoup  # type: ignore
from tenacity import retry, stop_after_attempt, wait_exponential  # type: ignore

BASE_URL = "http://ufcstats.com"
FIGHTERS_URL = f"{BASE_URL}/statistics/fighters?char=a&page=all"
USER_AGENT = "FightIQBot/0.1 (+https://example.com)"


@retry(stop=stop_after_attempt(3), wait=wait_exponential(multiplier=1, min=1, max=8))
def fetch(url: str) -> str:
  headers = {"User-Agent": USER_AGENT}
  response = requests.get(url, headers=headers, timeout=20)
  response.raise_for_status()
  time.sleep(0.5)
  return response.text


def parse_index(html: str) -> List[Dict[str, Any]]:
  soup = BeautifulSoup(html, "lxml")
  table = soup.select_one("table.b-statistics__table")
  if table is None:
    return []
  results: List[Dict[str, Any]] = []
  for row in table.select("tr.b-statistics__table-row"):
    cols = row.select("td")
    if len(cols) < 1:
      continue
    link = cols[0].select_one("a")
    if not link:
      continue
    results.append({
      "name": link.text.strip(),
      "profile_url": link.get("href")
    })
  return results


def iter_fighters() -> Iterator[Dict[str, Any]]:
  html = fetch(FIGHTERS_URL)
  for f in parse_index(html):
    yield f


if __name__ == "__main__":
  for f in iter_fighters():
    print(f)


