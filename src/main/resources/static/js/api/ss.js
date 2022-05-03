import Vue from 'vue'

export default {
    auth: obj => Vue.resource('/login').save(obj),
    reg: obj => Vue.resource('/registration').save(obj)
}