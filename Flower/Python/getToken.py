import requests

host = 'https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=8L4kurGZ4ti92FNFXDwggL5P&client_secret=Gq6Ni3krT90yPmGPvDP5pb79dEqQinWh'
response = requests.get(host)

# 服务器返回信息为JSON文本格式，重要参数包括：
# access_token： 要获取的Access Token
# expires_in： Access Token的有效期，(秒为单位，一般为1个月)

if response:
    print(response.json())
    print(response.json()['access_token'])