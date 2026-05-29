<template>
    <div id="app">

        <header>
            <topMenu/>
        </header>
        <main>
            <div class="container">

                <div class="row">
                    <div class="column col-md-2"></div>
                    <div class="col-md-8">
                            <router-view/>
                    </div>
                    <div class="column col-md-2"></div>

                </div>
            </div>
        </main>
    </div>
</template>



<script>
    import topMenu from "./menu/topMenu"

    export default {
        components: {
            topMenu
        }
    }
</script>