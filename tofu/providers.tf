terraform {
  required_providers {
    clevercloud = {
      source  = "CleverCloud/clevercloud"
      version = "0.4.1"
    }
    uca = {
      source  = "registry.terraform.io/florian-forestier/uca"
      version = "1.2.2"
    }
    ovh = {
      source  = "ovh/ovh"
      version = "1.2.0"
    }
  }
}

provider "clevercloud" {
  organisation = var.clevercloud_organisation
  token        = var.clevercloud_token
  secret       = var.clevercloud_secret
}

provider "uca" {
  endpoint   = "https://cloud-ui.edu.forestier.re/api/"
  user_token = var.uca_token
}

provider "ovh" {
  endpoint           = var.ovh_endpoint
  application_key    = var.ovh_application_key
  application_secret = var.ovh_application_secret
  consumer_key       = var.ovh_consumer_key
}