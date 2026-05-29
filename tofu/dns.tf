resource "ovh_domain_zone_record" "tpnote_record" {
  zone      = "uca-devops.ovh"
  subdomain = local.user_id_uca
  fieldtype = "A"
  target    = uca_server.my_server.ipv4
  ttl       = 3600
}

resource "ovh_domain_zone_record" "api_record" {
  zone      = "uca-devops.ovh"
  subdomain = "api.${local.user_id_uca}"
  fieldtype = "A"
  target    = uca_server.my_server.ipv4
  ttl       = 3600
}

resource "ovh_domain_zone_record" "grafana_record" {
  zone      = "uca-devops.ovh"
  subdomain = "grafana.${local.user_id_uca}"
  fieldtype = "A"
  target    = uca_server.my_server.ipv4
  ttl       = 3600
}

output "app_url" {
  value = "https://${local.user_id_uca}.uca-devops.ovh"
}
