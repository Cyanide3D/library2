<template>
    <v-container>
        <v-card
                flat
                class="pa-7"
                width="600"
                height="600"
        >
            <v-card-title class="title">
                Ваш профиль.
            </v-card-title>
            <v-card-actions>
            </v-card-actions>
            <v-divider></v-divider>
            <v-card-text>
                    <p>Ваш логин: {{ profile.username }}</p>
                    <p>Ваша роль: {{ profile.role }}</p>
            </v-card-text>
        </v-card>
        <v-divider></v-divider>
        <h2 align="center">Забронированные книги</h2>
        <v-data-table :headers="headers" :items="rents">
            <template v-slot:items="props">
                <td>{{ props.item.id }}</td>
                <td>{{ props.item.book.name }}</td>
                <td>{{ props.item.returned }}</td>
                <td>{{ props.item.rentedDate }}</td>
                <td>{{ props.item.dateToReturn }}</td>
                <td><v-btn v-if="profile.role === 'ADMIN'" v-bind:class="{'v-btn--disabled': !props.item.rented}">Снять бронь</v-btn><v-btn @click="onOpenExtendRentWindow(props.item)" v-bind:class="{'v-btn--disabled': isExtended(props.item)}">Продлить</v-btn></td>
            </template>
        </v-data-table>
        <v-dialog
                v-model="extendRentWindow"
                width="500"
                @click:outside="extendRentWindow = false"
        >
            <v-card>
                <v-card-title class="headline grey lighten-2">
                    Продление бронирования
                </v-card-title>

                <v-card-text>
                    <p>Введите кол-во дней на которое хотите продлить бронь.</p>
                    <p>Внимание! Бронь можно продлить только один раз и не более чем на 15 дней.</p>
                    <v-text-field label="Дни" v-model="rentDays"></v-text-field>
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="success"
                            text
                            @click="onExtentRent"
                            v-bind:class="{'v-btn--disabled': !(rentDays > 0 && rentDays < 16)}"
                    >
                        Продлить
                    </v-btn>
                    <v-btn
                            color="primary"
                            text
                            @click="extendRentWindow = false"
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
                    Вы уже продлевали бронь.
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

        <v-dialog
                v-model="successWindow"
                width="500"
                @click:outside="successWindow = false"
        >
            <v-card>
                <v-card-title class="headline grey lighten-2">
                    Успешно
                </v-card-title>

                <v-card-text>
                    Бронь успешно продлена на {{ rentDays }} дней.
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="primary"
                            text
                            @click="successWindow = false"
                    >
                        Закрыть
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
    import {mapState} from "vuex";
    import rent from "../api/rent";

    export default {
        name: "Profile",
        data() {
            return {
                rents: [],
                errorWindow: false,
                rentDays: 0,
                extendedRentId: 0,
                successWindow: false,
                extendRentWindow: false,
                headers: [
                    {text: 'ID', value: 'id'},
                    {text: 'Название книги', value: 'book.name'},
                    {text: 'Возвращена ли книга', value: 'returned'},
                    {text: 'Дата аренды', value: 'rentedDate'},
                    {text: 'Дата сдачи', value: 'dateToReturn'},
                ],
            }
        },
        methods: {
            onExtentRent: function () {
                this.extendRentWindow = false
                    rent.extend({bookId: this.extendedRentId, days: this.rentDays}).then(response => {
                        if (response.ok) {
                            this.successWindow = true
                            this.$router.push('/profile/' + this.profile.id)
                        }
                    })
            },
            onOpenExtendRentWindow: function (book) {
                this.extendRentWindow = true
                this.extendedRentId = book.id
            },
            isExtended: function (book) {
                let res = false
                this.rents.forEach(r => {
                    if (r.book.id === book.id) {
                        res = r.extended
                    }
                })

                return res
            },


        },
        computed: {
            ...mapState(['profile']),
        } ,

        created() {
            rent.allByUserId(this.$route.params.id).then(response => this.rents = response.body)
        }
    }
</script>

<style scoped>

</style>