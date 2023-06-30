<?php

namespace App\Entity;

use App\Repository\BillRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: BillRepository::class)]
class Bill
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id;

    #[ORM\Column(type: 'string', length: 50)]
    private $fullname;

    #[ORM\Column(type: 'string', length: 20)]
    private $phone;

    #[ORM\Column(type: 'string', length: 255)]
    private $cus_address;

    #[ORM\OneToOne(inversedBy: 'checkout', targetEntity: Order::class, cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(nullable: true)]
    private $info;
    
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getFullname(): ?string
    {
        return $this->fullname;
    }

    public function setFullname(string $fullname): self
    {
        $this->fullname = $fullname;

        return $this;
    }

    public function getPhone(): ?string
    {
        return $this->phone;
    }

    public function setPhone(string $phone): self
    {
        $this->phone = $phone;

        return $this;
    }

    public function getCusAddress(): ?string
    {
        return $this->cus_address;
    }

    public function setCusAddress(string $cus_address): self
    {
        $this->cus_address = $cus_address;

        return $this;
    }

    public function getInfo(): ?Order
    {
        return $this->info;
    }

    public function setInfo(Order $info): self
    {
        $this->info = $info;

        return $this;
    }
}
