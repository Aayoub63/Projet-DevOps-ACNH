import Vue from 'vue'
import Router from 'vue-router'
import fullLayout from "./components/fullLayout";
import index from "./components/index/index";
import privateProfile from "./components/profile/privateProfile"
import publicProfile from "./components/profile/publicProfile"
import musics from "./components/collections/musics";
import arts from "./components/collections/arts";
import legals from "./components/index/legals"
import fossils from "./components/collections/fossils";
import flowers from "./components/collections/flowers";
import flower_calculator from "./components/tools/flower_calculator";

const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/musics',
            component: fullLayout,
            children: [
                {
                    path: '',
                    component: musics
                }
            ]
        },
        {
            path: '/flowers',
            component: fullLayout,
            children: [
                {
                    path: 'calculator',
                    component: flower_calculator
                },
                {
                    path: '',
                    component: flowers
                }
            ]
        },
        {
            path: '/arts',
            component: fullLayout,
            children: [
                {
                    path: '',
                    component: arts
                }
            ]
        },
        {
            path: '/fossils',
            component: fullLayout,
            children: [
                {
                    path: '',
                    component: fossils
                }
            ]
        },
        {
          path: '/profile',
          component: fullLayout,
            children: [
                {
                    path: '',
                    component: privateProfile
                },
                {
                    path: ':name',
                    component: publicProfile
                }
            ]

        },
        {
            path: '/',
            component: fullLayout,
            children: [
                {
                    path: '',
                    component: index
                },
                {
                    path:'/legals',
                    component: legals
                }
            ]
        }
]
});

Vue.use(Router);

export default router