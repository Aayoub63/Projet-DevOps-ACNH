<script>
    export default {
        data() {
            return {
                onlyMissing: false,
                fullList: {},
                parsedPosseded: []
            }
        },

        created() {
            if(localStorage.token==null || localStorage.token===""){
                this.$router.push("/");
            }

            this.getFossils();
            this.getUserFossils();
        },

        computed: {
            apiurl() {
                return process.env.VUE_APP_API
            }
        },

        methods: {
            toggleVisibility() {
                this.onlyMissing = !this.onlyMissing;
                console.log(this.onlyMissing);
            },
            getFossils() {
                this.axios.get(process.env.VUE_APP_API + "fossils?size=1000&sort=dinosaurName", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.fullList = response.data.content;
                    })
            },
            getUserFossils() {
                this.axios.get(process.env.VUE_APP_API + "users/me/fossils", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.parsedPosseded = response.data.map(e => e.id);
                    })
            },

            toggleStatus(id) {
                if(this.parsedPosseded.includes(id)){
                    //We have to delete
                    this.axios.delete(process.env.VUE_APP_API+"users/me/fossils/"+id, {headers: {"Authorization": localStorage.token}})
                        .then(() => {
                            this.parsedPosseded = this.parsedPosseded.filter(function(value){ return value !== id;});
                        })
                        .catch(() => {
                            this.$awn.warning("Le fossile n'a pas pu être supprimée de votre liste !");
                        })
                } else {
                    //We have to add
                    this.axios.put(process.env.VUE_APP_API+"users/me/fossils/"+id, null, {headers: {"Authorization": localStorage.token}})
                    .then(() => {
                        this.parsedPosseded.push(id);
                    })
                    .catch(() => {
                        this.$awn.warning("Le fossile n'a pas pu être ajoutée à votre liste !");
                    })
                }
            }
        }
    }
</script>

<template>
    <div>
        <div class="jumbotron welcomebg">
            <div style="display: flex; justify-content: space-between;align-items: center;">
                <div>
                    <h1 class="display-3"><span class="leaf"></span>Mes fossiles ({{parsedPosseded.length}}/{{fullList.length}} possédés)</h1>
                </div>
                <div>
                    <b-button :class="(onlyMissing?'activehidebutton':'hidebutton') + ' fas fa-low-vision'"
                              @click="toggleVisibility"
                              v-b-tooltip="'Afficher ou masquer les fossiles déjà obtenues'"></b-button>
                </div>
            </div>
            <hr>


            <div class="flex-wrap" style="display: flex; justify-content: center">
                <div v-bind:key="i.id" v-for="i in fullList">
                    <b-card @click="toggleStatus(i.id)" bg-variant="default" :class="'floating-card ' + (parsedPosseded.includes(i.id) ? 'posseded' : 'missing')"
                            text-variant="white" v-if="!onlyMissing || !parsedPosseded.includes(i.id)">
                        <b-card-header><img :src="apiurl + i.image" alt="image" width="130px"/></b-card-header>
                        <b-card-text class="text-center" style="margin-bottom: 0;">
                            <b>{{i.dinosaurPart}}
                                <span v-if="i.dinosaurPart">
                                    {{(i.dinosaurName.startsWith("A") || i.dinosaurName.startsWith("E") || i.dinosaurName.startsWith("I")
                                    || i.dinosaurName.startsWith("O") || i.dinosaurName.startsWith("O") || i.dinosaurName.startsWith("U")
                                    || i.dinosaurName.startsWith("Y")) ? 'd\'' : 'de '}}</span>{{i.dinosaurName}}</b>
                        </b-card-text>
                    </b-card>
                </div>
            </div>


        </div>

    </div>

</template>