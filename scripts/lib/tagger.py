import re

def read_dict(dict_filename) -> dict:
    result = {}
    with open(f"../src/main/resources/{dict_filename}" , 'r') as dict_file:
        for line in dict_file.readlines():
            items = line.strip().split(',')
            if not len(items) == 2:
                continue
            word, code = items[0], items[1]
            result[word] = code
    return result

def get_tags(lang_dict: dict, feed: dict) -> set:
    texts = {feed.get('title', ''), feed.get('description', ''), feed.get('content', '')}
    clean = lambda text: re.sub(r"[,!?]", "", text).lower()
    texts = [clean(text) for text in texts if text]
    tags = set(lang_dict[key] for key in lang_dict for text in texts if key in text)
    return tags