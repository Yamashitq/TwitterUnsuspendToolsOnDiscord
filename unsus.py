import json

import requests
import time
from bs4 import BeautifulSoup
from twocaptcha import TwoCaptcha


def unsus(ID, Mail):
    solver = TwoCaptcha('')
    result = solver.recaptcha(url='https://twitter.com', sitekey="6LdxbBMTAAAAAMF-W0bh4IoNtiI7gYKiQdXVYY23")

    token = result["code"]

    if not token.startswith("03"):
        return "failed"

    response = requests.get('https://help.twitter.com/forms/general?subtopic=suspended')
    soup = BeautifulSoup(response.text, "lxml")
    csrf_token = soup.find(attrs={'name':'csrf-token'}).get('content')
    authenticity_token = soup.find(attrs={'id':'authenticity_token'}).get('value')
    support_session = response.cookies["_support_session"]

    headers = {
        'x-csrf-token': csrf_token,
        'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36',
        'cookie': 'at_check=true; auth_token=95ea41b686e3c88dd6ca0e52a31f82fc3f695a42; ct0=0112cfee1f6973c3bb879e311b2bad848402d6285a7e0491219242270117d5e6bc6cd3ec674775f7e87b6c2b935dacaf737f588b40c6ec5f9bfa262ff6df0157fe10947806ce29227e1467f4cda0be8f; _support_session=' + support_session + ';',
    }

    data = {
        'authenticity_token': authenticity_token,
        'regarding': 'suspended',
        'serviceCloud[Mobile_App__c]': 'TweetDeck',
        'serviceCloud[Subject]': 'Appealing an account suspension - @' + ID,
        'serviceCloud[Screen_Name__c]': ID,
        'serviceCloud[Form_Email__c]': Mail,
        'serviceCloud[SuppliedPhone]': '',
        'serviceCloud[Description]': '\u3053\u306E\u554F\u984C\u306F\u3069\u3053\u3067\u767A\u751F\u3057\u307E\u3059\u304B?: TweetDeck\r\n\u554F\u984C\u306E\u8A73\u7D30: \u307E\u3093\u3053\r\n\u6C0F\u540D: \u5C71\u4E0B\u96BC\u5E73\r\nTwitter\u30E6\u30FC\u30B6\u30FC\u540D: @' + ID + '\r\n\u30E1\u30FC\u30EB\u30A2\u30C9\u30EC\u30B9: ' + Mail + '\r\nn/a\r\n\u96FB\u8A71\u756A\u53F7(\u65E5\u672C\u672A\u5BFE\u5FDC) (\u4EFB\u610F): n/a\r\n',
        'serviceCloud[Referral_Client__c]': '',
        'serviceCloud[Source_Form__c]': 'locked_account',
        'serviceCloud[Type_of_Issue__c]': 'Not Available',
        'serviceCloud[Category__c]': 'Suspended',
        'g-recaptcha-response': token,
        'secondary_email': ''
    }

    requests.post('https://help.twitter.com/api/v1/tickets', headers=headers, data=data)
    return "success"


def check(screen_name=None):
    headers = {
        'authorization': 'Bearer AAAAAAAAAAAAAAAAAAAAANRILgAAAAAAnNwIzUejRCOuH5E6I8xnZz4puTs%3D1Zv7ttfk8LF81IUq16cHjhLTvJu4FA33AGWWjCpTnA',
        'x-guest-token': requests.post('https://api.twitter.com/1.1/guest/activate.json', headers = {'authorization': 'Bearer AAAAAAAAAAAAAAAAAAAAANRILgAAAAAAnNwIzUejRCOuH5E6I8xnZz4puTs%3D1Zv7ttfk8LF81IUq16cHjhLTvJu4FA33AGWWjCpTnA','User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100'}
).json()["guest_token"],
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100'
    }
    show = requests.get("https://api.twitter.com/1.1/users/show.json?screen_name=" + screen_name, headers=headers)
    return show.status_code


with open('unsus.json') as f:
    df = json.load(f)

unsus(df["id"], df["email"])

open(df["response"] + '.json', 'w')
