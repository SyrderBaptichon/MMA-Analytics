import argparse
import json
from scrapers.ufcstats import events as ufc_events
from scrapers.ufcstats import event_detail as ufc_event_detail
from scrapers.ufcstats import fighter_detail as ufc_fighter_detail


def cmd_events_list() -> None:
  data = list(ufc_events.iter_events())
  print(json.dumps(data, indent=2))


def main() -> None:
  parser = argparse.ArgumentParser(description="Fight-IQ data pipeline CLI")
  sub = parser.add_subparsers(dest="cmd")
  sub.add_parser("events:list")
  ev = sub.add_parser("event:detail")
  ev.add_argument("--url", required=True, help="Event detail URL from ufcstats.com")
  fp = sub.add_parser("fighter:detail")
  fp.add_argument("--url", required=True, help="Fighter profile URL from ufcstats.com")
  sub.add_parser("load:latest")
  args = parser.parse_args()
  if args.cmd == "events:list":
    cmd_events_list()
  elif args.cmd == "event:detail":
    html = ufc_event_detail.fetch(args.url)
    print(json.dumps(ufc_event_detail.parse_event_detail(html), indent=2))
  elif args.cmd == "fighter:detail":
    html = ufc_fighter_detail.fetch(args.url)
    print(json.dumps(ufc_fighter_detail.parse_profile(html), indent=2))
  elif args.cmd == "load:latest":
    events = list(ufc_events.iter_events())[:10]
    print(json.dumps(events, indent=2))
  else:
    parser.print_help()


if __name__ == "__main__":
  main()


