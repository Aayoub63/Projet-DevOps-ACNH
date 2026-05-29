import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
import router from './router.js'
import store from './store.js'
import axios from 'axios'
import VueAxios from 'vue-axios'
import VueAWN from "vue-awesome-notifications"
import { Datetime } from 'vue-datetime'

import 'bootstrap-vue/dist/bootstrap-vue.css'
import '@fortawesome/fontawesome-free/css/all.min.css'
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'
import 'vue-datetime/dist/vue-datetime.css'
import "vue-multiselect/dist/vue-multiselect.min.css";

Vue.use(Datetime)
Vue.use(BootstrapVue);
Vue.use(VueAxios, axios);
Vue.use(VueAWN, {durations: {global: 6000}, labels: {success: "Succès", warning: "Attention", alert: "Erreur !"}});


Vue.component('datetime', Datetime);

new Vue({
    router,
    store,
    render: h => h(App),
    created() {
        if (localStorage.token != null) {
            axios.get(process.env.VUE_APP_API + "users/me", {headers: {Authorization: localStorage.token}})
                .then(response => {
                    let obj = {
                        id: response.data.id,
                        username: response.data.username,
                        token: localStorage.token
                    };

                    this.$store.commit('login', obj);
                })
                .catch(error => {
                    console.log(error);
                    this.$store.commit('logout');
                    this.$router.push("/");
                    this.$awn.alert("Votre session a expirée, veuillez vous reconnecter.");
                })
        }
    },
}).$mount('#app');
