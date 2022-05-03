<template>
    <v-app>
        <v-toolbar dense>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
            <v-toolbar-title>Библиотека</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-menu v-if="isAdmin()" offset-y>
                <template v-slot:activator="{ on }">
                    <v-btn
                            flat
                            v-on="on"
                    >
                        Управление
                    </v-btn>
                </template>
                <v-list>
                    <v-list-tile
                            width="125"
                            v-for="(item, index) in items"
                            :key="index"
                            @click="onMenuClick(item)"
                    >
                        <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                    </v-list-tile>
                </v-list>
            </v-menu>
            <v-btn to="/" flat>Главная</v-btn>
            <v-btn v-if="profile" @click="toProfile" flat>Профиль</v-btn>
            <v-btn v-if="profile" to="/notification" flat>Уведомления <b v-bind:class="{'error--text': notifyCount > 0}"> ({{ notifyCount }})</b></v-btn>
            <v-btn v-if="profile" href="/logout" flat>Выйти</v-btn>
            <v-btn v-if="!profile" @click="login = true" flat>Войти</v-btn>
            <v-btn v-if="!profile" @click="registration = true" flat>Регистрация</v-btn>
        </v-toolbar>
        <v-content>
            <router-view @clearNotify="notifyCount=0"></router-view>
        </v-content>

        <v-dialog
                v-model="login"
                width="500"
                @click:outside="login = false"
        >
            <form method="post" action="login">
                <v-card>
                    <v-card-title class="headline grey lighten-2">
                        Авторизация
                    </v-card-title>

                    <v-card-text>
                        <v-text-field
                                label="Логин"
                                clearable
                                name="username"
                                v-model="username"
                        ></v-text-field>
                        <v-text-field
                                label="Пароль"
                                clearable
                                name="password"
                                v-model="password"
                        ></v-text-field>
                    </v-card-text>

                    <v-divider></v-divider>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                                color="success"
                                text
                                type="submit"

                        >
                            Войти
                        </v-btn>
                        <v-btn
                                color="primary"
                                text
                                @click="login = false"
                        >
                            Закрыть
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </form>
        </v-dialog>

        <v-dialog
                v-model="registration"
                width="500"
                @click:outside="registration = false"
        >
            <v-card>
                <v-card-title class="headline grey lighten-2">
                    Регистрация
                </v-card-title>

                <v-card-text>
                    <v-text-field
                            label="Логин"
                            clearable
                            v-model="username"
                    ></v-text-field>
                    <v-text-field
                            label="Пароль"
                            clearable
                            v-model="password"
                    ></v-text-field>
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="success"
                            text
                            @click="onRegistration"
                    >
                        Регистрация
                    </v-btn>
                    <v-btn
                            color="primary"
                            text
                            @click="registration = false"
                    >
                        Закрыть
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <v-dialog
                v-model="errorWindow"
                width="500"
                @click:outside="errorWindow = false"
        >
            <v-card>
                <v-card-title class="headline grey lighten-2">
                    Ошибка
                </v-card-title>

                <v-card-text>
                    Ошибка при вводе данных.
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="primary"
                            text
                            @click="errorWindow = false"
                    >
                        Закрыть
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-app>
</template>

<script>
    import { mapState } from 'vuex'
    import ss from "../api/ss";
    import notification from "../api/notification";

    export default {
        data: () => ({
            items: [
                {title: 'Книги', url: '/manage/book'},
                {title: 'Авторы', url: '/manage/author'},
            ],
            login: false,
            registration: false,
            username: "",
            password: "",
            errorWindow: false,
            notifyCount: 0,
        }),
        computed: mapState(['profile']),
        methods: {
            onMenuClick: function (item) {
                this.$router.push(item.url)
            },
            onRegistration: function () {
                this.registration = false
                ss.reg({"username" : this.username, "password" : this.password}).then(response => {
                    if (!response.ok) {
                        this.errorWindow = true
                    }
                })
                this.username = ''
                this.password = ''
            },
            toProfile: function () {
                this.$router.push('/profile/' + this.profile.id)
            },
            setNotifyCount: function () {
                if (this.profile) {
                    notification.count().then(response => {
                        if (response.ok) {
                            this.notifyCount = response.body
                        }
                    })
                }
            },
            isAdmin: function () {
                if (this.profile) {
                    if (this.profile.role === 'ADMIN') {
                        return true
                    }
                }
                return false
            }
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/')
            }
        },
        created() {
            setInterval(this.setNotifyCount, 4000)
        }
    }
</script>

<style>

</style>
