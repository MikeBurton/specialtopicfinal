package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

/**
 * Created by Mike on 16/04/2014.
 */
public class Category
{
    int id;
    public int getId(){return id;}
    String description;
    public String getDescription(){return description;}
    String name;
    public String getName(){return name;}

    public Category(int id,String description, String name)
    {
        this.id = id;
        this.description = description;
        this.name = name;


    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
