<template>
    <v-container>
        <v-card flat
                class="pa-4"
                width="600"
                height="600"
        >
            <v-card-title class="title">
                Информация об авторе.
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
                <div style="width: 45%; float: left">
                    <v-img :src="author.pic"/>
                </div>
                <div style="width: 45%; float: right">
                    <p><b>Имя: {{ author.name }}</b></p>
                    <p><b>Об авторе: {{ author.description }}</b></p>
                </div>
            </v-card-text>
        </v-card>
        <v-divider></v-divider>
        <h1 align="center">Книги автора:</h1>

        <v-layout class="pa-4">
            <v-flex xs12 sm6 offset-sm3>
                <v-card>
                    <v-card-actions>
                    </v-card-actions>
                    <v-container v-bind="{ [`grid-list-xl`]: true }" fluid>
                        <v-layout row wrap>
                            <v-flex
                                    class="pa-4"
                                    v-for="book in books"
                                    :key="book.id"
                                    xs4
                            >
                                <v-card tile flat hover @click="onClick(book)">
                                    <v-img
                                            :src="book.img"
                                            height="250px"
                                    ></v-img>
                                    <p class="pa-3">{{ book.name }}</p>
                                </v-card>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import authors from "../api/author";
    import books from "../api/books";

    export default {
        name: "ShowAuthor",
        data() {
            return {
                author: {},
                books: [],
            }
        },
        created() {
            authors.oneById(this.$route.params.id).then(response => this.author = response.body)
            books.oneByAuthorId(this.$route.params.id).then(response => this.books = response.body)
        },
        methods: {
            onClick: function (book) {
                this.$router.push('/book/' + book.id)
            }
        }
    }
</script>

<style scoped>

</style>