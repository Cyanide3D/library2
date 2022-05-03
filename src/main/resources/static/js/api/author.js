import Vue from 'vue'

const author = Vue.resource('/author')

export default {
    oneById: id => Vue.resource('/author/' + id).get(),
    delete: id =>  Vue.resource('/author/' + id).remove(),
    all: () => author.get(),
    save: obj => author.save(obj)
}