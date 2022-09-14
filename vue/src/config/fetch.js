import { baseUrl } from './env'
import VueCookies from 'vue-cookies'

export default async(url = '', data = {}, type = 'GET', ContentType = "json", method = 'fetch') => {
	type = type.toUpperCase();
	url = baseUrl + url;
    if (ContentType === "json") {
        ContentType = "application/x-www-form-urlencoded"
    }else {
        ContentType = "multipart/form-data"
    }
	if (type === 'GET' || ContentType === "multipart/form-data") {
		let dataStr = ''; //数据拼接字符串
		Object.keys(data).forEach(key => {
			dataStr += key + '=' + data[key] + '&';
		})

		if (dataStr !== '') {
			dataStr = dataStr.substr(0, dataStr.lastIndexOf('&'));
			url = url + '?' + dataStr;
		}
	}

	if (window.fetch && method == 'fetch') {
		let requestConfig = {
			credentials: 'include',
			method: type,
			headers: {
                'Authorization': VueCookies.get('Authorization'),
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			},
			mode: "cors",
			cache: "force-cache"
		}

		if (type === 'POST' && ContentType === "application/x-www-form-urlencoded") {
            console.log("post")
            console.log(JSON.stringify(data))
			Object.defineProperty(requestConfig, 'body', {
				value: JSON.stringify(data)
			})
		}

		try {
			const response = await fetch(url, requestConfig);
			const responseJson = await response.json();
            // console.log("responseJson",responseJson)
			return responseJson
		} catch (error) {
			throw new Error(error)
		}
	} else {
		return new Promise((resolve, reject) => {
			let requestObj;
			if (window.XMLHttpRequest) {
				requestObj = new XMLHttpRequest();
			} else {
				requestObj = new ActiveXObject;
			}

			let sendData = '';
			if (type == 'POST') {
				sendData = JSON.stringify(data);
			}

			requestObj.open(type, url, true);
			// requestObj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			requestObj.setRequestHeader("Content-type", ContentType);
			requestObj.send(sendData);

			requestObj.onreadystatechange = () => {
				if (requestObj.readyState == 4) {
					if (requestObj.status == 200) {
						let obj = requestObj.response
						if (typeof obj !== 'object') {
							obj = JSON.parse(obj);
						}
						resolve(obj)
					} else {
						reject(requestObj)
					}
				}
			}
		})
	}
}
