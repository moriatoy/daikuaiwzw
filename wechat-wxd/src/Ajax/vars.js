import { NO, dealCode } from '../Utils/utils.js'
// export const server_url = '/api/dc'
export const server_url = '/dc'

// export const server_url = '/api'
// export const server_url = '/wx-wwzz'
// export const server_url = ''

export const failAction = () => {
    //alert("服务器错误");
}
export const dumbWrapper = ({
    promise,
    successCB = NO,
    failCB = failAction
}) => {
    promise.then((ret) => {
        dealCode(ret, successCB)
    }, (e) => {
        failCB(e)
    })
}


