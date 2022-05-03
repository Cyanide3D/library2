<template>
    <v-container>
        <v-card v-for="notify in notifies" :key="notify.id" class="pa-4">
            <v-card-text>
                {{ notify.message }}
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script>
    import notification from "../api/notification";

    export default {
        name: "Notification",
        data() {
            return {
                notifies: [],
            }
        },
        created() {
            notification.get().then(response => {
                if (response.ok) {
                    this.notifies = response.body
                }
            })
            this.$emit('clearNotify')
        }
    }
</script>

<style scoped>

</style>