resource "uca_server" "my_server" {
  name     = "tpnote-${var.user_id}"
  username = "ubuntu"
  ssh_key  = var.ssh_public_key
}

resource "uca_security_group" "my_sg" {
  name = "sg-tpnote-${var.user_id}"
}

resource "uca_security_rule" "allow_ssh" {
  security_group_id = uca_security_group.my_sg.id
  name              = "allow-ssh"
  description       = "Ouverture du port 22 pour acceder a la VM"
  protocol          = "TCP"
  port              = 22
  direction         = "inbound"
  ip_range          = "0.0.0.0/0"
}

resource "uca_security_rule" "allow_http" {
  security_group_id = uca_security_group.my_sg.id
  name              = "allow-http"
  description       = "Ouverture du port 80 pour l'application"
  protocol          = "TCP"
  port              = 80
  direction         = "inbound"
  ip_range          = "0.0.0.0/0"
}

resource "uca_security_rule" "allow_https" {
  security_group_id = uca_security_group.my_sg.id
  name              = "allow-https"
  description       = "Ouverture du port 443 pour HTTPS"
  protocol          = "TCP"
  port              = 443
  direction         = "inbound"
  ip_range          = "0.0.0.0/0"
}

resource "uca_security_rule" "allow_traefik" {
  security_group_id = uca_security_group.my_sg.id
  name              = "allow-traefik"
  description       = "Ouverture du port 8080 pour Traefik"
  protocol          = "TCP"
  port              = 8080
  direction         = "inbound"
  ip_range          = "0.0.0.0/0"
}

resource "uca_security_rule" "allow_grafana" {
  security_group_id = uca_security_group.my_sg.id
  name              = "allow-grafana"
  description       = "Ouverture du port 9090 pour Grafana/Prometheus"
  protocol          = "TCP"
  port              = 9090
  direction         = "inbound"
  ip_range          = "0.0.0.0/0"
}

resource "uca_security_group_attachment" "my_attachment" {
  security_group_id = uca_security_group.my_sg.id
  server_id         = uca_server.my_server.id
}

output "vm_ip" {
  value = uca_server.my_server.ipv4
}

output "vm_username" {
  value = uca_server.my_server.username
}
