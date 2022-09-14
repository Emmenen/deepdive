import axios from 'axios';
import FormData from 'form-data';
import { baseUrl } from './env'
import VueCookies from 'vue-cookies'


export default async (url="",file="")=>{
    url = baseUrl + url;
    const data = new FormData();
    data.append('file', file);
    const config = {
        method: 'post',
        url: url,
        headers: {
            'Authorization': VueCookies.get('Authorization'),
            'Accept': 'application/json',
            'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>',
        },
        data : data
    };
    let data1
    await axios(config)
        .then(function (response) {
            setTimeout("5000")
            console.log("response=>",response)
            data1 = response.data;
            console.log('data=>',data)
            // return data;
        })
        .catch(function (error) {
            console.log(error);
            return "error";
        });
    return data1
}



