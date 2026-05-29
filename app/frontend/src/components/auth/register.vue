<script>

    import login from "./login";
    export default {
        components: {
            login
        },

        data() {
            return {
                display: false,
                form: {
                    username: "",
                    password: "",
                    email: ""
                },
                loading: false,
            }
        },

        methods: {
            show() {
                this.display = true;
            },

            register(){
                this.axios.post(process.env.VUE_APP_API+"users", this.form)
                    .then(() => {
                        this.$awn.success("Inscription réussie ! Connexion en cours...");
                        this.$refs.login.loginFromArgs(this.form.username, this.form.password);
                        this.form.username = "";
                        this.form.password = "";
                        this.form.email = "";
                        this.$bvModal.hide("modal");
                    })
                .catch(() => {
                    this.$awn.alert("Impossible de s'inscrire ! Le nom d'utilisateur ou l'adresse email est déjà utilisée.");
                })
            },

/*            login() {
                this.axios.post(process.env.VUE_APP_API + "login", this.form)
                    .then(response => {
                        this.axios.get(process.env.VUE_APP_API + "users/me", {headers: {"Authorization": response.headers.authorization}})
                            .then(response2 => {
                                let user = {
                                    id: response2.data.id,
                                    username: response2.data.username,
                                    token: response.headers.authorization
                                }
                                this.$store.commit("login", user);
                                this.$awn.success("Bienvenue sur Le Collectionneur !");
                                this.$bvModal.hide("modal");
                            })
                            .catch(() => {
                                this.$awn.alert("Erreur lors de la connexion !");
                            })
                    })
                    .catch(() => {
                        this.$awn.alert("Erreur lors de la connexion !");
                    })
            }
*/

        }

    }
</script>


<template>
    <b-modal v-model="display" no-close-on-backdrop id="modal" title="Inscription" size="md" hide-footer>
        <div class="text-center" v-if="loading">
            <b-spinner variant="info" label="Spinning"></b-spinner>
        </div>
        <div v-else>
            <b-form v-on:submit.prevent="register()">
                <b-form-group id="input-group-1" label="Nom d'utilisateur" label-for="input-1">
                    <b-form-input id="input-1" v-model="form.username" type="text" required/>
                </b-form-group>

                <b-form-group id="input-group-2" label="Mot de passe" label-for="input-2">
                    <b-form-input id="input-2" v-model="form.password" type="password"/>
                </b-form-group>

                <b-form-group id="input-group-3" label="Adresse mail" label-for="input-3">
                    <b-form-input id="input-3" v-model="form.email" type="email"/>
                </b-form-group>

                <div class="text-right footerButton">
                    <b-button type="reset" class="mt-3" @click="$bvModal.hide('modal')">Annuler</b-button>
                    <b-button type="submit" class="mt-3 btn-success" :disabled="form.username.length===0 || form.password.length===0">
                        S'inscrire
                    </b-button>

                </div>
            </b-form>
        </div>
        <login ref="login"></login>
    </b-modal>
</template>
