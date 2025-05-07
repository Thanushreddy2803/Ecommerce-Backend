package com.project.ecommerce_backend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
    private String name;

   @ManyToOne
   @JoinColumn(name="parent_category_id")
   private Category parentCategory;

    public Long getId() {
        return id;
    }

    public Category(Long id, String name, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    // Default constructor
    public Category() {
    }
    @Column(name="lavel")
    private int Lavel;

    public int getLavel() {
        return Lavel;
    }

    public void setLavel(int lavel) {
        Lavel = lavel;
    }
}
