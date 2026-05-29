<script>
    export default {
        data() {
            return {
                display: false,
                form: {
                    yesContent: ""
                },
                loading: false,
            }
        },

        methods: {
            show() {
                this.display = true;
            },

            deleteAccount() {
                this.axios.delete(process.env.VUE_APP_API + "users/me", {headers: {"Authorization": localStorage.token}})
                    .then(() => {
                        this.$store.commit("logout");
                        this.$awn.info("Votre compte a bien été supprimé. A bientôt sur le Collectionneur !");
                        this.$router.push("/");
                    })
                    .catch(() => {
                        this.$awn.alert("Erreur lors de la suppression du compte !");
                    })
            }
        }
    }
</script>


<template>
    <b-modal v-model="display" id="modal" title="Supprimer son compte" size="lg" hide-footer>
        <div class="text-center" v-if="loading">
            <b-spinner variant="info" label="Spinning"></b-spinner>
        </div>
        <div v-else>
            Êtes-vous sûr de vouloir supprimer votre compte ? Vous perdrez l'ensemble de vos collections. Cette action est <b><u>irréversible</u></b>. <br /><br />
            Pour confirmer, veuillez saisir "Oui" dans le champ en dessous, et appuyer sur le bouton rouge.


                <b-form-group id="input-group-1" label-for="input-1">
                    <b-form-input id="input-1" v-model="form.yesContent" type="text" required/>
                </b-form-group>

                <div class="text-right footerButton">
                    <b-button type="submit" class="mt-3 btn-danger" :disabled="form.yesContent.toLocaleLowerCase()!=='oui'" @click="deleteAccount()">
                        Supprimer le compte
                    </b-button>
                </div>
        </div>

    </b-modal>
</template>
