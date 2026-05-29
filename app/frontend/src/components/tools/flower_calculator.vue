<script>
    import ImageSelect from "./ImageSelect";

    export default {
        components: {
            ImageSelect
        },

        data() {
            return {
                onlyMissing: false,
                fullList: [],
                produces: [],
                imageSelect: null
            }
        },

        created() {
            if(localStorage.token==null || localStorage.token===""){
                this.$router.push("/");
            }

            this.getFlowers();
            this.getFlowerProduces();
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
            getFlowers() {
                this.axios.get(process.env.VUE_APP_API + "flowers?size=1000&sort=name", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.fullList = response.data.content.filter(e => e.name!=="Arrosoir d'or");
                    })
            },
            getFlowerProduces() {
                this.axios.get(process.env.VUE_APP_API + "flowersProduces?size=1000", {headers: {"Authorization": localStorage.token}})
                    .then(response => {
                        this.produces = response.data.content;
                    })
            }
        }
    }
</script>

<template>
    <div>
        <div class="jumbotron welcomebg">
            <div style="display: flex; justify-content: space-between;align-items: center;">
                <div>
                    <h1 class="display-3"><span class="leaf"></span>Calculateur de fleurs</h1>
                </div>
            </div>
            <hr>
            <div class="text-center" style="font-size: 16px;">
            <b>Sélectionnez dans la liste la fleur désirée; le calculateur vous affichera l'ensemble des croisements possibles.</b>
            </div>
            <ImageSelect v-model="imageSelect" :options="fullList" :show-labels="false" style="width: 100%; height: 160px;"/>

            <div v-if="imageSelect!=null">
            <h2>Liste des croisements pour : {{ imageSelect.name }}</h2>
            <div v-for="e in produces" v-bind:key="e.id">
                <ul>
                <li v-if="e.produces.id===imageSelect.id">
                    <img :src="apiurl+e.sourceA.image" width="100px;"/> <span style="font-size: 60px; vertical-align: middle">+</span> <img :src="apiurl+e.sourceB.image"  width="100px;"/>
                    <span style="font-size: 20px; vertical-align: middle; font-style: italic">({{e.sourceA.name}} + {{e.sourceB.name}})</span>
                </li>
                </ul>
            </div>
            <div class="text-center" style="font-size: 16px;">
                <b>Si rien ne s'affiche, c'est qu'aucun croisement n'existe pour obtenir cette plante !</b>
            </div>
            </div>
        </div>

    </div>

</template>