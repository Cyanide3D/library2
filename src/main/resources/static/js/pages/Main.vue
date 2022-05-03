<template>
    <v-layout class="pa-4">
        <v-flex xs12 sm6 offset-sm3>
            <v-card>
                <v-card-actions>
                </v-card-actions>
                <v-container v-bind="{ [`grid-list-xl`]: true }" fluid>
                    <v-layout row wrap>
                        <v-flex
                                v-for="book in books"
                                :key="book.id"
                                xs4
                        >
                            <v-card tile elevation="1" hover @click="onClick(book)">
                                <v-img
                                        :src="book.img"
                                        height="150px"
                                ></v-img>
                                <p class="pa-3">{{ book.name }}</p>
                            </v-card>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
    import books from "../api/books";

    export default {
        name: "Main",
        data() {
            return {
                books: []
            }
        },
        created() {
            books.get().then(response => this.books = response.body)
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