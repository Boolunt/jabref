package org.jabref.model.entry.field;

import org.jabref.model.entry.KeywordList;

import java.util.*;

public enum SpecialField implements Field {

    PRINTED("printed",
            SpecialFieldValue.PRINTED
    ),

    PRIORITY("priority",
            SpecialFieldValue.CLEAR_PRIORITY,
            SpecialFieldValue.PRIORITY_HIGH,
            SpecialFieldValue.PRIORITY_MEDIUM,
            SpecialFieldValue.PRIORITY_LOW
    ),
    
    QUALITY("quality",
            SpecialFieldValue.CLEAR_QUALITY,
            SpecialFieldValue.QUALITY_LOW,
            SpecialFieldValue.QUALITY_MEDIUM,
            SpecialFieldValue.QUALITY_HIGH
    ),

    RANKING("ranking",
            SpecialFieldValue.CLEAR_RANK,
            SpecialFieldValue.RANK_1,
            SpecialFieldValue.RANK_2,
            SpecialFieldValue.RANK_3,
            SpecialFieldValue.RANK_4,
            SpecialFieldValue.RANK_5
    ),

    READ_STATUS("readstatus",
            SpecialFieldValue.CLEAR_READ_STATUS,
            SpecialFieldValue.READ,
            SpecialFieldValue.SKIMMED,
            SpecialFieldValue.TO_BE_READ
    ),

    RELEVANCE("relevance",
            SpecialFieldValue.RELEVANT,
            SpecialFieldValue.NOT_RELEVANT
    );

    private final List<SpecialFieldValue> values;
    private final KeywordList keywords;
    private final HashMap<String, SpecialFieldValue> map;
    private final String fieldName;

    SpecialField(String fieldName, SpecialFieldValue... values) {
        this.fieldName = fieldName;
        this.values = new ArrayList<>();
        this.keywords = new KeywordList();
        this.map = new HashMap<>();
        for (SpecialFieldValue value : values) {
            this.values.add(value);
            value.getKeyword().ifPresent(keywords::add);
            value.getFieldValue().ifPresent(fieldValue -> map.put(fieldValue, value));
        }
    }

    public List<SpecialFieldValue> getValues() {
        return this.values;
    }

    public KeywordList getKeyWords() {
        return this.keywords;
    }

    public static Optional<SpecialField> fromName(String name) {
        return Arrays.stream(SpecialField.values())
                     .filter(field -> field.getName().equalsIgnoreCase(name))
                     .findAny();
    }

    public boolean isSingleValueField() {
        return this.values.size() == 1;
    }

    public Optional<SpecialFieldValue> parseValue(String value) {
        return Optional.ofNullable(map.get(value));
    }

    @Override
    public Set<FieldProperty> getProperties() {
        return EnumSet.noneOf(FieldProperty.class);
    }

    @Override
    public String getName() {
        return fieldName;
    }

    @Override
    public boolean isStandardField() {
        return false;
    }
}
