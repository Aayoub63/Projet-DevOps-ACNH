<script>
    export default {
        data() {
            return {
                onlyMissing: false,
                fullList: {},
                parsedPosseded: [],
                cheatMode: false
            }
        },

        created() {
            if(localStorage.token==null || localStorage.token===""){
                this.$router.push("/");
            }

            this.getArts();
            this.getUserArts();
        },

        computed: {
            apiurl() {
                return process.env.VUE_APP_API
            }
        },

        methods: {
            toggleVisibility() {
                this.onlyMissing = !this.onlyMissing;
            },
            toggleCheat(){
                this.cheatMode = !this.cheatMode;
            },

            getArts() {
                this.axios.get(process.env.VUE_APP_API + "arts?size=1000&sort=type", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.fullList = response.data.content;
                        this.fullList.forEach(e => {
                            switch (e.type) {
                                case "SCULPTURE":
                                    e.type = "Sculpture";
                                    break;
                                default:
                                    e.type = "Peinture";
                            }
                            });
                    })
            },
            getUserArts() {
                this.axios.get(process.env.VUE_APP_API + "users/me/arts", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.parsedPosseded = response.data.map(e => e.id);
                    })
            },

            toggleStatus(id) {
                if(this.parsedPosseded.includes(id)){
                    //We have to delete
                    this.axios.delete(process.env.VUE_APP_API+"users/me/arts/"+id, {headers: {"Authorization": localStorage.token}})
                        .then(() => {
                            this.parsedPosseded = this.parsedPosseded.filter(function(value){ return value !== id;});
                        })
                        .catch(() => {
                            this.$awn.warning("L'oeuvre d'art n'a pas pu être supprimée de votre liste !");
                        })
                } else {
                    //We have to add
                    this.axios.put(process.env.VUE_APP_API+"users/me/arts/"+id, null, {headers: {"Authorization": localStorage.token}})
                    .then(() => {
                        this.parsedPosseded.push(id);
                    })
                    .catch(() => {
                        this.$awn.warning("L'oeuvre d'art n'a pas pu être ajoutée à votre liste !");
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
                    <h1 class="display-3"><span class="leaf"></span>Mes oeuvres d'art ({{parsedPosseded.length}}/{{fullList.length}} possédés)</h1>
                </div>
                <div>
                    <b-button :class="(cheatMode?'activehidebutton':'hidebutton') + ' fas fa-gamepad'"
                              @click="toggleCheat"
                              v-b-tooltip="'Afficher ou masquer le mode triche'"></b-button>
                    <span style="margin-left:15px;"></span>
                    <b-button :class="(onlyMissing?'activehidebutton':'hidebutton') + ' fas fa-low-vision'"
                              @click="toggleVisibility"
                              v-b-tooltip="'Afficher ou masquer les oeuvres d\'art déjà obtenues'"></b-button>
                </div>
            </div>
            <hr>


            <div v-if="!cheatMode" class="flex-wrap" style="display: flex; justify-content: center">
                <div v-bind:key="i.id" v-for="i in fullList">
                    <b-card @click="toggleStatus(i.id)" bg-variant="default" :class="'floating-card ' + (parsedPosseded.includes(i.id) ? 'posseded' : 'missing')"
                            text-variant="white" v-if="!onlyMissing || !parsedPosseded.includes(i.id)">
                        <b-card-header><img :src="apiurl + i.realImage" alt="image" height="120px"/></b-card-header>
                        <b-card-text class="text-center" style="margin-bottom: 0;"><b>{{i.name}}</b></b-card-text>
                    </b-card>
                </div>
            </div>
            <div v-else>
                <div v-bind:key="i.id" v-for="i in fullList">
                    <b-card @click="toggleStatus(i.id)" bg-variant="default" :class="'floating-cheat-card ' + (parsedPosseded.includes(i.id) ? 'posseded' : 'missing')"
                            text-variant="white" v-if="!onlyMissing || !parsedPosseded.includes(i.id)">
                        <b-card-text class="text-center"><b style="font-size: 16px">{{i.name}} | {{i.realName}}</b></b-card-text>
                        <div class="flex-wrap" style="display: flex; justify-content: space-around; text-align: center; color: #000;">
                            <div><img :src="apiurl+i.realImage" alt="Image réelle" height="240px"/><br /><b>Image réelle</b></div>
                            <div v-if="i.hasFake" style="width:20px;"></div>
                            <div v-if="i.hasFake"><img :src="apiurl+i.fakeImage" alt="Fausse image" height="240px"/><br /><b>Fausse image</b></div>
                        </div>
                        <b-card-text v-if="i.hasFake" style="text-align: center"><b style="color:#333"><i class="fas fa-info-circle"></i>  {{i.difference_descriptor}}</b></b-card-text>
                        <b-card-text v-else style="text-align: center"><b style="color:#333"><i class="fas fa-info-circle"></i> Il n'y a pas de faux pour cette oeuvre !</b></b-card-text>
                    </b-card>
                </div>
            </div>


        </div>

    </div>

</template>