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
  organisation = var.CC_ORGANISATION
  token        = var.CC_TOKEN
  secret       = var.CC_SECRET
}

provider "uca" {
  endpoint   = "https://cloud-ui.edu.forestier.re/api/"
  user_token = var.UCA_TOKEN   
}

provider "ovh" {
  endpoint           = var.OVH_ENDPOINT
  application_key    = var.OVH_APPLICATION_KEY
  application_secret = var.OVH_APPLICATION_SECRET
  consumer_key       = var.OVH_CONSUMER_KEY
}