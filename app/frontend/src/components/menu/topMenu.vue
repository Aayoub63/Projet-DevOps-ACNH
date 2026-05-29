<script>

    import login from "../auth/login";
    import register from "../auth/register";

    export default {
        components: {
            login,
            register
        },

        computed: {
            user_id() { return this.$store.state.user.id; },
            user_name() { return this.$store.state.user.username; }
        },

        methods: {
            doLogout(){
                this.$store.commit("logout");
                this.$router.push("/");
                this.$awn.success("Vous avez bien été déconnecté. A bientôt !");
            }
        }
    }
</script>

<template>
    <div>
        <b-navbar class="customhead" toggleable="sm" type="dark">
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item to="/"><img src="/images/logo.png" width="150px;" style="margin-top:-9px;"/></b-nav-item>
                    <b-nav-item to="/musics" v-if="user_id!==null">Mes musiques</b-nav-item>
                    <b-nav-item to="/arts" v-if="user_id!==null">Mes oeuvres d'art</b-nav-item>
                    <b-nav-item to="/fossils" v-if="user_id!==null">Mes fossiles</b-nav-item>
                    <b-nav-item to="/flowers" v-if="user_id!==null">Mes fleurs</b-nav-item>
                </b-navbar-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item to="/" v-if="user_id===null" @click.prevent="$refs.login.show()">Connexion</b-nav-item>
                    <b-nav-item to="/" v-if="user_id===null" @click.prevent="$refs.register.show()">Inscription</b-nav-item>
                    <b-nav-item to="/profile" v-if="user_id!==null">Mon profil - {{user_name}}</b-nav-item>
                    <b-nav-item v-if="user_id!==null" @click.prevent="doLogout()">Déconnexion</b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <login ref="login"></login>
        <register ref="register"></register>

    </div>
</template>