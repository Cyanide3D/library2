<template>
    <v-container>
        <v-card
                flat
                class="pa-4"
                width="600"
                height="600"
        >
            <v-card-title class="title">
                Информация о книге.
            </v-card-title>
            <v-card-actions>
                <v-btn @click="onRent" v-bind:class="{'v-btn--disabled': book.rented}" v-if="profile" type="success">Забронировать</v-btn>
            </v-card-actions>
            <v-divider></v-divider>
            <v-card-text>
                <div style="width: 45%; float: left">
                    <v-img :src="book.img"/>
                </div>
                <div style="width: 45%; float: right">
                    <p><b>Название: {{ book.name }}</b></p>
                    <p><b>Жанр: {{ book.genre }}</b></p>
                    <p><b>Автор: <a @click="onClick">{{ book.author.name }}</a></b></p>
                    <p><b>Описание: {{ book.description }}</b></p>
                    <br>
                    <v-divider></v-divider>
                    <h3 class="error--text" v-if="book.rented">Книга уже была кем то забронирована.</h3>
                </div>
            </v-card-text>
        </v-card>

        <v-dialog
                v-model="rent"
                width="500"
                @click:outside="rent = false"
        >
            <v-card>
                <v-card-title class="headline grey lighten-2">
                    Бронирование
                </v-card-title>

                <v-card-text>
                    Бронируя книгу вы обязаны будете вернуть её в библиотеку по истечению 30 дней с момента бронирования. У вас будет возможность единожды продлить бронирование книги на 15 дней.
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="success"
                            text
                            @click="onAcceptRent"
                    >
                        Забронировать
                    </v-btn>
                    <v-btn
                            color="primary"
                            text
                            @click="rent = false"
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
                    Что то пошло не так
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
    </v-container>
</template>

<script>
    import books from "../api/books";
    import rent from "../api/rent";
    import {mapState} from "vuex";

    export default {
        name: "ShowBook",
        data() {
            return {
                book: {},
                rent: false,
                errorWindow: false
            }
        },
        computed: mapState(['profile']),
        methods: {
            onClick: function () {
                this.$router.push('/author/' + this.book.author.id)
            },
            onRent: function () {
                this.rent = true
            },
            onAcceptRent: function () {
                this.rent = false
                rent.offer({userId: this.profile.id, bookId: this.book.id}).catch(e => this.errorWindow = true)
            },
            onCancelRent: function () {
                rent.cancel(this.book.id)
            }
        },
        created() {
            books.oneById(this.$route.params.id).then(response => this.book = response.body)
        }
    }
</script>

<style scoped>

</style>