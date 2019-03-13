// 毫秒转年月日
const DateFormat = (str, fmt) => {
    let o = {
        'M+': str.getMonth() + 1,
        'd+': str.getDate(),
        'h+': str.getHours(),
        'm+': str.getMinutes(),
        's+': str.getSeconds(),
        'q+': Math.floor((str.getMonth() + 3) / 3),
        'S': str.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (str.getFullYear() + '').substr(4 - RegExp.$1.length));
    for (let k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
        }
    }
    return fmt;
}

const extend = (o,n,override) => {
    for(var key in n){
        if(n.hasOwnProperty(key) && (!o.hasOwnProperty(key) || override)){
            if (typeof n[key] !== "object" || n[key] instanceof Array) o[key]=n[key];
            else {
                extend(o[key],n[key],override)
            }
        }
    }
    return o;
}
const joinPara = (url, opt) => {
    let date = new Date().getTime();
    url += '?';
    for(let key in opt) {
        url += `${key}=${opt[key]}&`
    }
    url += `timestamp=${date}`;
    return url;
}
// const Ajax = {
//   get: function(url, opt, success, fail) {
//     let endUrl = joinPara(url, opt);
//     // let date = new Date().getTime();
//     var xhr = new XMLHttpRequest();
//   //   url += '?'
//   //  for(let key in opt) {
//   //    url += `${key}=${opt[key]}&`
//   //  }
//   //   url += `timestamp=${date}`;
//     xhr.open('GET', endUrl, true);
//     xhr.onreadystatechange = function() {
// 			// readyState == 4说明请求已完成
// 			if (xhr.readyState == 4) {
// 				if (xhr.status == 200 || xhr.status == 304) {
// 					// 从服务器获得数据
// 					success.call(this, xhr.responseText);
// 				} else {
// 					fail.call(this, xhr.responseText);
// 				}
// 			}
//     };
//     xhr.send();
//   },
//   // datat应为'a=a1&b=b1'这种字符串格式，在jq里如果data为对象会自动将对象转成这种字符串格式
//   post: function (url, data, success, fail) {
//     var xhr = new XMLHttpRequest();
//     xhr.open("POST", url, true);
//     // 添加http头，发送信息至服务器时内容编码类型
//     xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//     xhr.onreadystatechange = function() {
//       if (xhr.readyState == 4) {
// 				if (xhr.status == 200 || xhr.status == 304) {
// 					// 从服务器获得数据
// 					success.call(this, xhr.responseText);
// 				} else {
// 					fail.call(this, xhr.responseText);
// 				}
// 			}
//     };
//     xhr.send(data);
//   }
// }

const Ajax = (url,opt,methods) => {
    return new Promise(function(resove,reject){
        methods = methods || 'POST';
        var xmlHttp = null;
        if (XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        } else {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        };
        var params = [];
        for (var key in opt){
            if(!!opt[key] || opt[key] === 0){
                params.push(key + '=' + opt[key]);
            }
        };
        var postData = params.join('&');
        if (methods.toUpperCase() === 'POST') {
            xmlHttp.open('POST', url, true);
            xmlHttp.setRequestHeader('Content-Type', 'application/json;charset=utf-8');
            xmlHttp.send(JSON.stringify(opt));
        }else if (methods.toUpperCase() === 'GET') {
            xmlHttp.open('GET', url + '?' + postData, true);
            xmlHttp.send(null);
        }else if(methods.toUpperCase() === 'DELETE'){
            xmlHttp.open('DELETE', url + '?' + postData, true);
            xmlHttp.send(null);
        }
        xmlHttp.onreadystatechange = function () {
            // if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            //   resove(JSON.parse(xmlHttp.responseText));
            // }
            if (xmlHttp.readyState == 4) {
                if (xmlHttp.status == 200 || xmlHttp.status == 304) {
                    // 从服务器获得数据
                    resove(JSON.parse(xmlHttp.responseText));
                } else {
                    reject(JSON.parse(xmlHttp.responseText));
                }
            }
        };
    });
}

// deepCopy
const deepCopy = (obj, cache = []) => {
    if (obj === null || typeof obj !== 'object') {
        return obj;
    }
    const hit = find(cache, c => c.original === obj);
    if (hit) {
        return hit.copy;
    }

    const copy = Array.isArray(obj)
        ? []
        : {};
    cache.push({
        original: obj,
        copy
    });

    Object.keys(obj).forEach(key => {
        copy[key] = deepCopy(obj[key], cache);
    });
    return copy;
};
export {
    DateFormat,
    extend,
    joinPara,
    deepCopy,
    Ajax
}