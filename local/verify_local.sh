#!/bin/bash
# Vérifie la stack locale : healthchecks Docker, URLs de l'appli et métriques Prometheus.
# Même logique que le job verify_deploy de la CI, mais sur localhost.

echo "== Healthchecks Docker"
for service in backend frontend; do
  ok=0
  for i in $(seq 1 18); do
    status=$(docker inspect --format '{{.State.Health.Status}}' $(docker compose ps -q $service))
    echo "$service : $status (essai $i/18)"
    if [ "$status" = "healthy" ]; then ok=1; break; fi
    sleep 10
  done
  if [ "$ok" != "1" ]; then echo "ECHEC : $service n'est pas healthy"; exit 1; fi
done

echo "== URLs"
for url in http://localhost:8000/ http://localhost:8080/arts http://localhost:3000/api/health; do
  code=$(curl -s -o /dev/null -w "%{http_code}" "$url")
  echo "$url -> HTTP $code"
  if [ "$code" != "200" ]; then echo "ECHEC : $url"; exit 1; fi
done

echo "== Cibles Prometheus"
targets=$(curl -s http://localhost:9090/api/v1/targets | grep -o '"scrapePool":"[^"]*"\|"health":"[^"]*"' | cut -d'"' -f4 | paste - -)
echo "$targets"
if echo "$targets" | grep -qv "up$"; then echo "ECHEC : une cible Prometheus n'est pas up"; exit 1; fi

echo "== L'actuator ne doit PAS être joignable sur le port public 8080"
code=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/actuator/prometheus)
echo "http://localhost:8080/actuator/prometheus -> HTTP $code"
if [ "$code" = "200" ]; then echo "ECHEC : les métriques sont exposées publiquement"; exit 1; fi

echo "Tout est OK"
