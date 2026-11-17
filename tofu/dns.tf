# Trois sous-domaines pointent vers la VM ; Traefik route ensuite selon le nom demandé.
resource "ovh_domain_zone_record" "app" {
  zone      = "uca-devops.ovh"
  subdomain = local.user_id_uca
  fieldtype = "A"
  target    = uca_server.vm.ipv4
  ttl       = 3600
}

resource "ovh_domain_zone_record" "api" {
  zone      = "uca-devops.ovh"
  subdomain = "api.${local.user_id_uca}"
  fieldtype = "A"
  target    = uca_server.vm.ipv4
  ttl       = 3600
}

resource "ovh_domain_zone_record" "grafana" {
  zone      = "uca-devops.ovh"
  subdomain = "grafana.${local.user_id_uca}"
  fieldtype = "A"
  target    = uca_server.vm.ipv4
  ttl       = 3600
}

output "app_url" {
  value = "https://${local.user_id_uca}.uca-devops.ovh"
}
