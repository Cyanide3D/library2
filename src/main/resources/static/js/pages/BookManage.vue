<template>
    <v-container>
        <v-btn @click="newBookWindow = true">Добавить книгу</v-btn>
        <v-text-field
                v-model="name"
                label="Search"
                class="mx-4"
        ></v-text-field>
        <v-data-table :search="name" :headers="headers" :items="books">
            <template v-slot:items="props">
                <td @click="onRowClick(props.item)">{{ props.item.id }}</td>
                <td @click="onRowClick(props.item)">{{ props.item.name }}</td>
                <td @click="onRowClick(props.item)">{{ props.item.rented }}</td>
                <td><v-btn @click="onDelete(props.item)">X</v-btn><v-btn @click="onCancelRent(props.item)" v-bind:class="{'v-btn--disabled': !props.item.rented}">Снять аренду</v-btn></td>
            </template>
        </v-data-table>
        <v-dialog
                v-model="newBookWindow"
                width="500"
                @click:outside="newBookWindow = false"
        >
            <v-card>
                <v-card-title class="headline grey lighten-2">
                    Добавление новой книги
                </v-card-title>

                <v-card-text>
                    <v-text-field
                            label="Название"
                            clearable
                            v-model="bookName"
                    ></v-text-field>
                    <v-text-field
                            label="Ссылка на картинку"
                            clearable
                            v-model="bookImage"
                    ></v-text-field>
                    <v-textarea
                            label="Описание"
                            clearable
                            v-model="bookDescription"
                    ></v-textarea>
                    <v-select
                            v-model="bookAuthor"
                            :items="authors"
                            item-text="name"
                            label="Автор"
                    ></v-select>
                    <v-select
                            v-model="bookGenre"
                            :items="genres"
                            label="Жанр"
                    ></v-select>
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="success"
                            text
                            @click="onAddBook"
                    >
                        Добавить
                    </v-btn>
                    <v-btn
                            color="primary"
                            text
                            @click="newBookWindow = false"
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
                    Проверьте правильность введёных данных в полях
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
    import authors from "../api/author";
    import rent from "../api/rent";

    export default {
        name: "BookManage",
        data() {
            return {
                name: '',
                errorWindow: false,

                bookName: '',
                bookDescription: '',
                bookImage: '',
                bookAuthor: {},
                bookGenre: '',

                authors: [],
                genres: [],

                newBookWindow: false,
                books: [],
                headers: [
                    {text: 'ID', value: 'id'},
                    {text: 'Название', value: 'name'},
                    {text: 'Аренда', value: 'rented'},
                    {text: 'Действия'},
                ],
            }
        },
        created() {
            books.getAll().then(response => this.books = response.body)
            books.genres().then(response => this.genres = response.body)
            authors.all().then(response => this.authors = response.body)
        },
        methods: {
            onRowClick: function (book) {
                this.$router.push('/book/' + book.id)
            },
            onDelete: function (book) {
                books.delete(book.id).then(response => {
                    if (response.ok) {
                        let index = this.books.findIndex(b => b.id === book.id)
                        this.books.splice(index, 1)
                    }
                })
            },
            onAddBook: function() {
                let authorId = -1;
                this.authors.forEach(e => {
                    if (e.name === this.bookAuthor) {
                        authorId = e.id
                    }
                })
                books.save({name: this.bookName, description: this.bookDescription, img: this.bookImage, author: authorId, genre: this.bookGenre}).then(response => {
                        if (response.ok) {
                            this.books.push(response.body)
                        }
                    }).catch(e => this.errorWindow = true)
                this.newBookWindow = false
                this.bookAuthor = ''
                this.bookGenre = ''
                this.bookImage = ''
                this.bookName = ''
                this.bookDescription = ''
            },
            onCancelRent: function (book) {
                rent.cancel(book.id)
            }
        }
    }
</script>

<style scoped>

</style>