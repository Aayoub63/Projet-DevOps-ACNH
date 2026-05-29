<script>

    export default {
        data() {
            return {
                "usermusics": [],
                "userarts": [],
                "userfossils": [],
                "userflowers": [],
                "musics": [],
                "arts": [],
                "fossils": [],
                "flowers": [],
                "user": {},
                "onlyMissing":false,
            }
        },

        mounted() {
            this.getInfos();
            this.getCollections();
        },

        computed: {
            apiurl() {
                return process.env.VUE_APP_API;
            }
        },

        methods: {
            getCollections() {

                this.axios.get(process.env.VUE_APP_API + "flowers?size=1000&sort=name", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.flowers = response.data.content;
                    })


                this.axios.get(process.env.VUE_APP_API + "arts?size=1000&sort=type")
                    .then(response => {
                        this.arts = response.data.content;
                        this.arts.forEach(e => {
                            switch (e.type) {
                                case "SCULPTURE":
                                    e.type = "Sculpture";
                                    break;
                                default:
                                    e.type = "Peinture";
                            }
                        });
                    })

                this.axios.get(process.env.VUE_APP_API + "fossils?size=1000&sort=dinosaurName", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.fossils = response.data.content;
                    })

                this.axios.get(process.env.VUE_APP_API + "musics?size=1000&sort=name", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.musics = response.data.content;
                        this.musics.forEach(e => {
                            switch (e.type) {
                                case "HIDDEN":
                                    e.type = "Cachée";
                                    break;
                                case "MOODY":
                                    e.type = "Mauvaise humeur";
                                    break;
                                case "GOOD":
                                    e.type = "Au top !";
                                    break;
                                case "RELAX":
                                    e.type = "Relax";
                                    break;
                                case "BLUES":
                                    e.type = "Victime du blues";
                                    break;
                                case "HARD_TO_TELL":
                                    e.type = "Difficile a dire";
                                    break;
                                default:
                                    e.type = "Spéciale";
                            }
                        });
                    })


            },

            toggleVisibility(){
                this.onlyMissing=!this.onlyMissing;
            },

            getInfos() {
                this.axios.get(process.env.VUE_APP_API + "users?search=" + this.$route.params.name)
                    .then(response => {
                        if (response.status !== 200) {
                            this.$awn.alert("Cet utilisateur n'existe pas !");
                            this.router.push("/");
                        }
                        this.user = response.data.content[0];

                        this.axios.get(process.env.VUE_APP_API + "users/" + this.user.id + "/arts")
                            .then(response => {
                                this.userarts = response.data.map(e => e.id);

                            })
                            .catch(() => {
                                this.$awn.alert("Erreur lors de la récupération des oeuvres d'art !");
                            })
                        this.axios.get(process.env.VUE_APP_API + "users/" + this.user.id + "/fossils")
                            .then(response => {
                                this.userfossils = response.data.map(e => e.id);

                            })
                            .catch(() => {
                                this.$awn.alert("Erreur lors de la récupération des fossiles !");
                            })
                        this.axios.get(process.env.VUE_APP_API + "users/" + this.user.id + "/musics")
                            .then(response => {
                                this.usermusics = response.data.map(e => e.id);

                            })
                            .catch(() => {
                                this.$awn.alert("Erreur lors de la récupération des musiques !");
                            })

                        this.axios.get(process.env.VUE_APP_API + "users/" + this.user.id + "/flowers")
                            .then(response => {
                                this.userflowers = response.data.map(e => e.id);
                            })
                            .catch(() => {
                                this.$awn.alert("Erreur lors de la récupération des musiques !");
                            })
                    })
                    .catch(() => {
                        this.$awn.alert("Cet utilisateur n'existe pas !");
                        this.router.push("/");
                    })
            }
        }


    }
</script>

<template>
    <div>
        <b-jumbotron class="jumbotron welcomebg">

            <div style="display: flex; justify-content: space-between;align-items: center;">
                <div>
                    <h1 class="display-3"><span class="leaf"></span>Profil de {{user.username}} <span v-if="user.switchCode"> - {{user.switchCode}}</span></h1>
                </div>
                <div>
                    <b-button :class="(onlyMissing?'activehidebutton':'hidebutton') + ' fas fa-low-vision'"
                              @click="toggleVisibility"
                              v-b-tooltip="'Changer le mode d\'affichage (possédés/manquants)'"></b-button>
                </div>
            </div>

            <hr>


            <h2>Collection - Musiques ({{usermusics.length}}/{{musics.length}} possédés)</h2>
            <div v-if="usermusics.length===0">Cet utilisateur n'a aucune musique !</div>
            <div>
                <div class="flex-wrap" style="display: flex; justify-content: center">
                    <div v-bind:key="i.id" v-for="i in musics">
                        <b-card v-if="(!onlyMissing && usermusics.includes(i.id) || (onlyMissing && !usermusics.includes(i.id)))"
                                bg-variant="default" :class="'floating-card ' + (usermusics.includes(i.id) ? 'posseded' : 'missing')" text-variant="white">
                            <b-card-header><img :src="apiurl + i.image" alt="image" width="130px"/></b-card-header>
                            <b-card-text class="text-center" style="margin-bottom: 0;"><b>{{i.name}}</b></b-card-text>
                            <b-card-text class="text-center">Collection "<i>{{i.type}}</i>"</b-card-text>
                        </b-card>
                    </div>
                </div>
            </div>

            <br/><br/><br/>

            <h2>Collection - Oeuvres d'art ({{userarts.length}}/{{arts.length}} possédés)</h2>
            <div v-if="userarts.length===0">Cet utilisateur n'a aucune oeuvre d'art !</div>

            <div>
                <div class="flex-wrap" style="display: flex; justify-content: center">
                    <div v-bind:key="i.id" v-for="i in arts">
                            <b-card v-if="(!onlyMissing && userarts.includes(i.id) || (onlyMissing && !userarts.includes(i.id)))"
                                    bg-variant="default" :class="'floating-card ' + (userarts.includes(i.id) ? 'posseded' : 'missing')" text-variant="white">
                            <b-card-header><img :src="apiurl + i.realImage" alt="image" height="120px"/></b-card-header>
                            <b-card-text class="text-center" style="margin-bottom: 0;"><b>{{i.name}}</b></b-card-text>
                        </b-card>
                    </div>
                </div>
            </div>

            <br/><br/><br/>

            <h2>Collection - Fossiles ({{userfossils.length}}/{{userfossils.length}} possédés)</h2>
            <div v-if="userfossils.length===0">Cet utilisateur n'a aucun fossile !</div>

            <div>
                <div class="flex-wrap" style="display: flex; justify-content: center">
                    <div v-bind:key="i.id" v-for="i in fossils">
                        <b-card v-if="(!onlyMissing && userfossils.includes(i.id) || (onlyMissing && !userfossils.includes(i.id)))"
                                bg-variant="default" :class="'floating-card ' + (userfossils.includes(i.id) ? 'posseded' : 'missing')" text-variant="white">

                            <b-card-header><img :src="apiurl + i.image" alt="image" height="120px"/></b-card-header>
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

            <br/><br/><br/>

            <h2>Collection - Fleurs ({{userflowers.length}}/{{flowers.length-1}} possédés)</h2>
            <div v-if="userflowers.length===0">Cet utilisateur n'a aucune fleur !</div>

            <div>
                <div class="flex-wrap" style="display: flex; justify-content: center">
                    <div v-bind:key="i.id" v-for="i in flowers">
                        <b-card v-if="(!onlyMissing && userflowers.includes(i.id) || (onlyMissing && !userflowers.includes(i.id)))"
                                bg-variant="default" :class="'floating-card ' + (userflowers.includes(i.id) ? 'posseded' : 'missing')" text-variant="white">

                            <b-card-header><img :src="apiurl + i.image" alt="image" height="120px"/></b-card-header>
                            <b-card-text class="text-center" style="margin-bottom: 0;"><b>{{i.name}}</b></b-card-text>
                        </b-card>
                    </div>
                </div>
            </div>
        </b-jumbotron>

    </div>
</template>