variable "instances" {
  default = ["Database"]
}

variable "db_name" {
  default = "mariadb"
}

resource "clevercloud_addon" "database" {
  name                 = "mariadb-Database"
  plan                 = "dev"
  third_party_provider = "mysql-addon"
  region               = "par"
}

resource "local_sensitive_file" "export" {
  filename = "mariadb-Database.sh"
  content = templatefile("mysql.sh.template", { 
    config = clevercloud_addon.database.configurations 
  })
}
