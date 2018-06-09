from lxml import html, etree
import requests


def get_tvn_content(address: str) -> str:
    utf8_parser = etree.XMLParser(encoding='utf-8')
    page = requests.get(address)
    try:
        tree = html.fromstring(page.content.decode("utf8"))
    except UnicodeDecodeError:
        print(f"UnicodeDecodeError on {address}")
        return ""
    items = tree.xpath('//div[@class="articleDetailHolder"]/article/*[not(aside)]')
    if not items:
        items = tree.xpath('//article')
    if not items:
        items = tree.xpath('//div[@class="contentFromCMS"]')
    if not items:
        items = tree.xpath('//div[@class="fromUsers content"]')
    return "\n".join([" ".join(item.text_content().split()) for item in items])

if __name__ == '__main__':
    print(get_tvn_content("http://tvn24bis.pl/redirect/dworczyk-wyprawki-szkolne-zostana-wprowadzone-od-wrzesnia-2018,829693.html"))
