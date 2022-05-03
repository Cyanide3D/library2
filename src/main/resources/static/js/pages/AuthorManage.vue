<template>
    <v-container>
        <v-btn @click="newAuthorWindow = true">Добавить автора</v-btn>
        <v-text-field
                v-model="name"
                label="Search"
                class="mx-4"
        ></v-text-field>
        <v-data-table :search="name" :headers="headers" :items="authors">
            <template v-slot:items="props">
                <td @click="onRowClick(props.item)">{{ props.item.id }}</td>
                <td @click="onRowClick(props.item)">{{ props.item.name }}</td>
                <td><v-btn @click="onDelete(props.item)">X</v-btn></td>
            </template>
        </v-data-table>
        <v-dialog
                v-model="newAuthorWindow"
                width="500"
                @click:outside="newAuthorWindow = false"
        >
            <v-card>
                <v-card-title class="headline grey lighten-2">
                    Добавление нового автора
                </v-card-title>

                <v-card-text>
                    <v-text-field
                            label="Имя"
                            clearable
                            v-model="authorName"
                    ></v-text-field>
                    <v-text-field
                            label="Ссылка на картинку"
                            clearable
                            v-model="authorImage"
                    ></v-text-field>
                    <v-textarea
                            label="Об авторе"
                            clearable
                            v-model="authorDescription"
                    ></v-textarea>
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
                            @click="newAuthorWindow = false"
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
    import authors from "../api/author";

    export default {
        name: "AuthorManage",
        data() {
            return {
                headers: [
                    {text: 'ID', value: 'id'},
                    {text: 'Имя', value: 'name'},
                ],
                name: '',
                newAuthorWindow: false,
                authors: [],
                errorWindow: false,

                authorName: '',
                authorImage: '',
                authorDescription: ''
            }
        },
        created() {
            authors.all().then(response => this.authors = response.body)
        },
        methods: {
            onDelete: function (author) {
                authors.delete(author.id).then(response => {
                    if (response.ok) {
                        let index = this.authors.findIndex(b => b.id === author.id)
                        this.authors.splice(index, 1)
                    }
                })
            },
            onRowClick: function (book) {
                this.$router.push('/author/' + book.id)
            },
            onAddBook: function () {
                authors.save({name: this.authorName, pic: this.authorImage, description: this.authorDescription}).then(response => {
                    if (response.ok) {
                        this.authors.push(response.body)
                    }
                }).catch(e => this.errorWindow = true)
                this.authorName = ''
                this.authorImage = ''
                this.authorDescription = ''
                this.newAuthorWindow = false
            }
        }
    }
</script>

<style scoped>

</style>