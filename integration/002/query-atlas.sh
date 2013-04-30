

cat > atlas-query-002.adql << 'EOF'

    SELECT
        twomass.ra AS tmra,
        atlas.ra   AS atra,
        (twomass.ra - atlas.ra) AS difra,
        twomass.dec AS tmdec,
        atlas.dec   AS tadec,
        (twomass.ra - atlas.ra) AS difdec,
        neighbour.distanceMins AS dist
    FROM
        query_schema.twomass_psc AS twomass,
        query_schema.atlasSource AS atlas,
        query_schema.atlasSourceXtwomass_psc AS neighbour
    WHERE
        twomass.ra  BETWEEN '55.0' AND '55.9'
    AND
        twomass.dec BETWEEN '20.0' AND '22.9'
    AND
        atlas.ra   BETWEEN '55.0' AND '55.9'
    AND
        atlas.dec  BETWEEN '20.0' AND '22.9'
    AND
        neighbour.masterObjID = atlas.sourceID
    AND
        neighbour.slaveObjID = twomass.pts_key
    AND
        neighbour.distanceMins < 1E-3

EOF

#
# Create the ADQL query.
POST "${queryschema?}/queries/create" \
    --data-urlencode "adql.schema.query.create.name=query-$(unique)" \
    --data-urlencode "adql.schema.query.create.store=${metabasename?}/${userschema?}" \
    --data-urlencode "adql.schema.query.create.query@atlas-query-002.adql" \
    | tee atlas-query.json | ./pp
atlasquery=$(cat atlas-query.json | ident)

#
# Run the ADQL query.
runquery "${atlasquery?}"


