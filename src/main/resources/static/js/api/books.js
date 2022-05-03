import Vue from 'vue'

const books = Vue.resource('/book')

export default {
    get: () => books.get(),
    getAll: () => Vue.resource('/book/all').get(),
    oneById: id => Vue.resource('/book/' + id).get(),
    oneByAuthorId: id => Vue.resource('/book/author/' + id).get(),
    delete: id => Vue.resource('/book/' + id).remove(id),
    genres: () => Vue.resource('/book/genres').get(),
    save: obj => Vue.resource('/book').save(obj)
}