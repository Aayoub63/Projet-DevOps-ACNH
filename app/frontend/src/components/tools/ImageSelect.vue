<template>
    <multiselect v-bind="allBindings" v-on="$listeners" style="width:160px; height: 160px;">
        <template slot="singleLabel" slot-scope="{ option }">
            <img class="option__image" :src="apiurl+option.image" :alt="option.name" />
            <span class="option__desc">
        <span class="option__title">{{ option.name }}</span>
      </span>
        </template>

        <template slot="option" slot-scope="{ option }">
            <img class="option__image" :src="apiurl+option.image" :alt="option.name" />
            <span class="option__desc">
        <span class="option__title">{{ option.name }}</span>
      </span>
        </template>
    </multiselect>
</template>

<script>
    import Multiselect from "vue-multiselect";
    import MultiselectMixin from "vue-multiselect/src/multiselectMixin";

    export default {
        components: {
            Multiselect
        },

        props: MultiselectMixin.props,
        computed: {
            allBindings() {
                // Need to proxify both props and attrs, for example for showLabels
                return { ...this.$props, ...this.$attrs };
            },
            apiurl() {
                return process.env.VUE_APP_API
            }

        }
    };
</script>

<style lang="scss" scoped>
    .multiselect ::v-deep {
        .option__image {
            max-height: 100px;
        }

        .option__desc,
        .option__image {
            display: inline-block;
            vertical-align: middle;
        }
    }
</style>
