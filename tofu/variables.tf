variable "user_id_uca" {
  type        = string
  description = "Identifiant UCA de l'utilisateur"
  default     = ""
}

variable "user_id" {
  type = string
}

variable "CC_ORGANISATION" {
  type      = string
  sensitive = true
}

variable "CC_TOKEN" {
  type      = string
  sensitive = true
}

variable "CC_SECRET" {
  type      = string
  sensitive = true
}

variable "UCA_TOKEN" {
  type      = string
  sensitive = true
}

variable "OVH_ENDPOINT" {
  type = string
}

variable "OVH_APPLICATION_KEY" {
  type = string
}

variable "OVH_APPLICATION_SECRET" {
  type      = string
  sensitive = true
}

variable "OVH_CONSUMER_KEY" {
  type      = string
  sensitive = true
}

variable "ssh_public_key" {
  type        = string
  description = "Clé publique SSH pour se connecter à la VM"
}