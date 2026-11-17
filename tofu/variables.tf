variable "user_id" {
  description = "Identifiant de l'utilisateur, utilisé dans le nom de la VM et du groupe de sécurité"
  type        = string
}

variable "user_id_uca" {
  description = "Sous-domaine de l'application. S'il est vide, on utilise <user_id>-uca"
  type        = string
  default     = ""
}

variable "ssh_public_key" {
  description = "Clé publique SSH installée sur la VM, utilisée par Ansible pour s'y connecter"
  type        = string
}

variable "uca_token" {
  description = "Jeton d'accès à l'API du cloud UCA"
  type        = string
  sensitive   = true
}

variable "ovh_endpoint" {
  description = "Point d'accès de l'API OVH, par exemple ovh-eu"
  type        = string
}

variable "ovh_application_key" {
  description = "Clé d'application de l'API OVH"
  type        = string
  sensitive   = true
}

variable "ovh_application_secret" {
  description = "Secret d'application de l'API OVH"
  type        = string
  sensitive   = true
}

variable "ovh_consumer_key" {
  description = "Clé consommateur de l'API OVH"
  type        = string
  sensitive   = true
}

variable "clevercloud_organisation" {
  description = "Organisation Clever Cloud qui héberge la base MariaDB"
  type        = string
  sensitive   = true
}

variable "clevercloud_token" {
  description = "Jeton de l'API Clever Cloud"
  type        = string
  sensitive   = true
}

variable "clevercloud_secret" {
  description = "Secret de l'API Clever Cloud"
  type        = string
  sensitive   = true
}
