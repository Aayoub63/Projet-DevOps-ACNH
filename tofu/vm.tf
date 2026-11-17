resource "uca_server" "vm" {
  name     = "acnh-${var.user_id}"
  username = "ubuntu"
  ssh_key  = var.ssh_public_key
}

# Seuls les ports utiles sont ouverts : SSH pour Ansible, HTTP et HTTPS pour Traefik.
# Les autres services (Prometheus, actuator...) restent internes au réseau Docker.
resource "uca_security_group" "vm" {
  name = "sg-acnh-${var.user_id}"
}

resource "uca_security_rule" "allow_ssh" {
  security_group_id = uca_security_group.vm.id
  name              = "allow-ssh"
  description       = "SSH, pour le deploiement avec Ansible"
  protocol          = "TCP"
  port              = 22
  direction         = "inbound"
  ip_range          = "0.0.0.0/0"
}

resource "uca_security_rule" "allow_http" {
  security_group_id = uca_security_group.vm.id
  name              = "allow-http"
  description       = "HTTP, redirige vers HTTPS par Traefik"
  protocol          = "TCP"
  port              = 80
  direction         = "inbound"
  ip_range          = "0.0.0.0/0"
}

resource "uca_security_rule" "allow_https" {
  security_group_id = uca_security_group.vm.id
  name              = "allow-https"
  description       = "HTTPS, point d'entree de l'application"
  protocol          = "TCP"
  port              = 443
  direction         = "inbound"
  ip_range          = "0.0.0.0/0"
}

resource "uca_security_group_attachment" "vm" {
  security_group_id = uca_security_group.vm.id
  server_id         = uca_server.vm.id
}

output "vm_ip" {
  value = uca_server.vm.ipv4
}

output "vm_username" {
  value = uca_server.vm.username
}
