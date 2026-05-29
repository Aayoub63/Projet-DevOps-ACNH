<script>
    import deleteAccount from "./private/deleteAccount";
    export default {
        data(){
            return {
                userData:{
                    username: "",
                    email: "",
                    switchCode: "",
                    password: "",
                    repeat: "",
                }
            }
        },

        components: {
            deleteAccount
        },
        computed: {
            user_name() { return this.$store.state.user.username; },
            url() { return process.env.VUE_APP_FRONT;},
            switchValid() { return this.userData.switchCode.length===0 || this.userData.switchCode.match('^SW-[0-9]{4}-[0-9]{4}-[0-9]{4}$');}
        },

        mounted() {
            if(localStorage.token==null || localStorage.token===""){
                this.$router.push("/");
            }

            this.getInfos()
        },

        methods: {
            updateInfos(){
                this.axios.patch(process.env.VUE_APP_API+"users/me", this.userData, {headers: {"Authorization":localStorage.token}})
                .then(() => {
                    this.$awn.success("Les informations ont bien été mise à jour !");
                    this.getInfos();
                })
                .catch(() => {
                    this.$awn.alert("Impossible de mettre les informations à jour...");
                })
            },
            getInfos(){
                this.axios.get(process.env.VUE_APP_API+"users/me", {headers: {"Authorization":localStorage.token}})
                .then(response => {
                    response.data.password="";
                    response.data.repeat="";
                    if(response.data.switchCode==null){
                        response.data.switchCode="";
                    }

                    this.userData = response.data;
                })
                .catch(() => {
                    this.$awn.alert("Impossible de récupérer vos informations de profil !");
                    this.$router.push("/");
                })
            }
        }
    }
</script>

<template>
    <div>
        <b-jumbotron class="welcomebg">
            <template v-slot:header><span class="leaf"></span>Mon profil - {{userData.username}}</template>
            <hr>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Adresse mail</span>
                </div>
                <b-form-input type="text" class="form-control" v-model="userData.email"/>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Mot de passe</span>
                </div>
                <b-form-input type="password" class="form-control" v-model="userData.password" placeholder="Nouveau mot de passe"/>
                <b-form-input type="password" class="form-control" v-model="userData.repeat" placeholder="Répétez le mot de passe"/>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Code Switch Online *</span>
                </div>
                <b-form-input type="text" class="form-control" v-model="userData.switchCode" placeholder="SW-0000-0000-0000" pattern="^SW-[0-9]{4}-[0-9]{4}-[0-9]{4}$" />
            </div>
            <sub style="top:-15px;"><i>* Note : En saisissant votre code Switch, vous acceptez que celui-ci soit diffusé publiquement. Pour le retirer, mettez ce champ à vide et cliquez sur "Mettre à jour".</i>
            </sub>
            <div style="text-align: right">
            <b-button variant="info" style="width:140px;" :disabled="!switchValid || userData.password!==userData.repeat" @click="updateInfos()">Mettre à jour</b-button>
            </div>
        </b-jumbotron>

        <b-jumbotron class="welcomebg">
            <template v-slot:header>Mon lien de profil</template>
            <hr>
            Toutes les personnes peuvent voir votre collection a l'adresse suivante : <a :href="url+'profile/'+user_name" target="_blank" rel="nofollow">{{url}}profile/{{user_name}}</a>.
            Cette adresse est publique, et ne requiert pas d'inscription.
        </b-jumbotron>

        <b-jumbotron class="welcomebg">
            <template v-slot:header>Actions douteuses</template>
            <hr>
            <div style="display:flex; justify-content: space-around">
            <b-button variant="danger" style="width: 40%" @click="$refs.deleteaccount.show()">Supprimer mon compte</b-button>
            </div>

            <delete-account ref="deleteaccount"></delete-account>
        </b-jumbotron>
    </div>
</template>