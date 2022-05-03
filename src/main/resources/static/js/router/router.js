import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from 'pages/Main.vue'
import ShowBook from 'pages/ShowBook.vue'
import ShowAuthor from 'pages/ShowAuthor.vue'
import Profile from 'pages/Profile.vue'
import BookManage from 'pages/BookManage.vue'
import AuthorManage from 'pages/AuthorManage.vue'
import Notification from 'pages/Notification.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: Main },
    { path: '/book/:id', component: ShowBook, props: true },
    { path: '/author/:id', component: ShowAuthor, props: true },
    { path: '/profile/:id', component: Profile, props: true },
    { path: '/manage/book', component: BookManage },
    { path: '/manage/author', component: AuthorManage },
    { path: '/notification', component: Notification },
]

export default new VueRouter({
    mode: 'history',
    routes
})
