import Vue from "vue";

export default {
    get: () => Vue.resource('/notification').get(),
    count: () => Vue.resource('/notification/count').get(),
}