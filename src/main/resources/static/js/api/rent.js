import Vue from 'vue'

const customActions = {
    pch: {method: 'PATCH', url: '/rent'},
}

export default {
    allByUserId: id => Vue.resource('/rent/user/' + id).get(),
    oneById: id =>  Vue.resource('/rent/' + id).get(),
    extend: obj => Vue.resource('/rent', {}, customActions).pch(obj),
    offer: obj => Vue.resource('/rent').save(obj),
    cancel: id => Vue.resource('/rent/' + id).remove(),
}