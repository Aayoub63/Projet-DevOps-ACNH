resource "clevercloud_addon" "database" {
  name                 = "mariadb-Database"
  plan                 = "dev"
  third_party_provider = "mysql-addon"
  region               = "par"
}

# Écrit les accès à la base dans mariadb-Database.sh (fichier ignoré par Git),
# pour pouvoir ensuite les enregistrer dans OpenBao.
resource "local_sensitive_file" "mariadb_credentials" {
  filename = "mariadb-Database.sh"
  content = templatefile("mysql.sh.template", {
    config = clevercloud_addon.database.configurations
  })
}
