import {
    server_url
} from './vars.js'
import {
    NO
} from '../Utils/utils.js'
import {
    dumbWrapper
} from './vars.js'
import request from 'superagent'

const factory = (url) => (params) => {
    return Promise.resolve(
        new Promise((resolve, reject) => {
            request.get(server_url + url).set('Content-Type', 'application/x-www-form-urlencoded').query(params).end((error, res) => {
                error ? reject(error) : resolve(res.body);
            })
        })
    )
};
//获取贷款详情
export const getCredit = factory('/w/cashUser/getCashLoan?id=102');
