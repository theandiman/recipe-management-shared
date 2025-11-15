# Recipe Management Shared Models

This repository contains shared models and types used across the Recipe Management platform services.

## Overview

The shared models provide a consistent interface for recipe data exchange between:
- **Frontend** (React/TypeScript)
- **AI Service** (Spring Boot/Java)
- **Storage Service** (Spring Boot/Java)

## Models

### Recipe Model

The core `Recipe` model includes all recipe-related fields with consistent naming and types across services.

#### Key Fields
- `recipeName`: Recipe title (consistent naming)
- `ingredients`: List of ingredient strings with quantities
- `instructions`: Step-by-step cooking instructions
- `servings`: Number of servings
- `nutritionalInfo`: Structured nutritional data
- `tips`: Recipe tips and additional information
- `source`: Origin of recipe ("ai-generated", "manual", etc.)

#### Timing Fields
- `prepTimeMinutes` / `cookTimeMinutes`: Integer minutes
- `prepTime` / `cookTime`: Human-readable strings (e.g., "15 minutes")
- `totalTimeMinutes`: Calculated total time

## Usage

### TypeScript (Frontend)

```typescript
import { Recipe, NutritionalInfo, RecipeTips } from '@recipe-management/shared';

// Use the shared types
const recipe: Recipe = {
  recipeName: "Spaghetti Carbonara",
  ingredients: ["400g spaghetti", "200g pancetta"],
  instructions: ["Boil pasta", "Fry pancetta"],
  servings: 4,
  source: "manual"
};
```

### Java (Backend Services)

```java
import com.recipe.shared.model.Recipe;
import com.recipe.shared.model.NutritionalInfo;

// Use the shared models
Recipe recipe = Recipe.builder()
    .recipeName("Spaghetti Carbonara")
    .ingredients(Arrays.asList("400g spaghetti", "200g pancetta"))
    .instructions(Arrays.asList("Boil pasta", "Fry pancetta"))
    .servings(4)
    .source("manual")
    .build();
```

## Migration Guide

### From Existing Models

#### Frontend Changes
Replace the existing `Recipe` interface in `src/types/nutrition.ts` with:
```typescript
import { Recipe } from '@recipe-management/shared';
```

#### AI Service Changes
Replace `RecipeDTO` with the shared `Recipe` model. Key mapping:
- `recipeName` → `recipeName` (already consistent)
- `estimatedTimeMinutes` → `totalTimeMinutes`
- `estimatedTime` → `totalTime`

#### Storage Service Changes
Replace the existing `Recipe` entity with the shared model. Key mapping:
- `title` → `recipeName`
- `nutrition` (Map) → `nutritionalInfo` (NutritionalInfo)
- `tips` (Map) → `tips` (RecipeTips)

## Building

### TypeScript
```bash
npm run build:ts
```

### Java
```bash
mvn compile
```

### Both
```bash
npm run build  # Builds both TypeScript and Java
```

## Publishing

```bash
# Publish TypeScript package to npm
npm publish

# Publish Java package to Maven repository
mvn deploy
```

## Compatibility

The shared models are designed to be backward compatible where possible, with utility methods for data conversion between old and new formats.

## Contributing

When adding new fields to the Recipe model:
1. Update both TypeScript and Java versions
2. Ensure backward compatibility
3. Update this README
4. Add conversion utilities if needed# Test commit
