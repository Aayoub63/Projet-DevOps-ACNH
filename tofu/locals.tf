locals {
  # Sous-domaine de l'application : la valeur fournie, sinon <user_id>-uca
  user_id_uca = var.user_id_uca != "" ? var.user_id_uca : "${var.user_id}-uca"
}
